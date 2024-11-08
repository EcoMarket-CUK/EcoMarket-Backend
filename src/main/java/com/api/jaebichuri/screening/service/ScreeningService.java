package com.api.jaebichuri.screening.service;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.mapper.AuctionMapper;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import com.api.jaebichuri.global.service.AwsS3Service;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.screening.dto.ScreeningDto;
import com.api.jaebichuri.screening.dto.ScreeningListDto;
import com.api.jaebichuri.screening.entity.AuctionScreening;
import com.api.jaebichuri.screening.entity.AuctionScreeningImage;
import com.api.jaebichuri.screening.enums.AuctionScreeningStatus;
import com.api.jaebichuri.screening.mapper.ScreeningMapper;
import com.api.jaebichuri.screening.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final AuctionRepository auctionRepository;
    private final ScreeningMapper screeningMapper;
    private final AuctionMapper auctionMapper;
    private final AwsS3Service awsS3Service;

    @Transactional
    public String createAuctionScreening(ScreeningDto screeningDto, List<MultipartFile> images, Member member) throws IOException {
        validateImages(images);

        AuctionScreening auctionScreening = screeningMapper.toAuctionScreening(screeningDto);
        auctionScreening.setSeller(member);
        auctionScreening.setScreeningStatus(AuctionScreeningStatus.PRICE_REVIEW);

        List<AuctionScreeningImage> uploadedImages = uploadImagesToS3(images);

        if (!uploadedImages.isEmpty()) {
            auctionScreening.setImages(uploadedImages);
        }

        screeningRepository.save(auctionScreening);

        return "Auction screening has been created with ID: " + auctionScreening.getId();
    }

    @Transactional(readOnly = true)
    public List<ScreeningListDto> getScreenings(Member member) {
        List<AuctionScreening> screenings = screeningRepository.findAllBySeller(member);
        return screeningMapper.toScreeningListDto(screenings);
    }

    @Transactional(readOnly = true)
    public List<?> getAuctionsByStatus(AuctionStatus status, Member member) {
        List<Auction> auctions = auctionRepository.findBySellerAndAuctionStatus(member, status);

        if (status == AuctionStatus.ONGOING) {
            return auctionMapper.toOngoingProductDtoList(auctions);
        } else if (status == AuctionStatus.ENDED) {
            return auctionMapper.toEndedProductDtoList(auctions);
        }

        return Collections.emptyList();
    }

    private List<AuctionScreeningImage> uploadImagesToS3(List<MultipartFile> images) throws IOException {
        List<AuctionScreeningImage> uploadedImages = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageUrl = awsS3Service.uploadImageToS3(image);
            Boolean isRepresentative = uploadedImages.isEmpty();

            AuctionScreeningImage auctionScreeningImage = AuctionScreeningImage.builder()
                    .imageUrl(imageUrl)
                    .isRepresentative(isRepresentative)
                    .build();

            uploadedImages.add(auctionScreeningImage);
        }

        return uploadedImages;
    }

    private void validateImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            throw new CustomException(ErrorStatus._MIN_IMAGES_REQUIRED);
        } else if (images.size() > 3) {
            throw new CustomException(ErrorStatus._MAX_IMAGES_EXCEEDED);
        }
    }

}

