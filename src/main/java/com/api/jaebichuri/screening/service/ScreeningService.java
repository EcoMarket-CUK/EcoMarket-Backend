package com.api.jaebichuri.screening.service;

import com.api.jaebichuri.auction.dto.OngoingAuctionProductDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.mapper.AuctionMapper;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import com.api.jaebichuri.global.service.AwsS3Service;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.screening.dto.EndedAuctionProductDto;
import com.api.jaebichuri.screening.dto.ScreeningDto;
import com.api.jaebichuri.screening.dto.ScreeningListDto;
import com.api.jaebichuri.screening.entity.AuctionScreening;
import com.api.jaebichuri.screening.entity.AuctionScreeningImage;
import com.api.jaebichuri.screening.mapper.ScreeningMapper;
import com.api.jaebichuri.screening.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

        for (MultipartFile image : images) {
            try {
                String imageUrl = awsS3Service.uploadImageToS3(image);
                AuctionScreeningImage auctionScreeningImage = AuctionScreeningImage.builder()
                        .imageUrl(imageUrl)
                        .auctionScreening(auctionScreening)
                        .build();
                auctionScreening.addImage(auctionScreeningImage);
            } catch (IOException e) {
                throw new CustomException(ErrorStatus._IMAGE_UPLOAD_FAILED);
            }
        }

        screeningRepository.save(auctionScreening);

        return "Auction screening has been created";
    }

    @Transactional(readOnly = true)
    public List<ScreeningListDto> getAllScreenings(Member member) {
        List<AuctionScreening> screenings = screeningRepository.findAllBySeller(member);
        return screeningMapper.toScreeningListDto(screenings);
    }

    private void validateImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            throw new CustomException(ErrorStatus._MIN_IMAGES_REQUIRED);
        } else if (images.size() > 3) {
            throw new CustomException(ErrorStatus._MAX_IMAGES_EXCEEDED);
        }
    }

    @Transactional(readOnly = true)
    public List<OngoingAuctionProductDto> getOngoingAuctions(Member member) {
        List<Auction> ongoingAuctions = auctionRepository.findBySellerAndAuctionStatus(member, AuctionStatus.ONGOING);
        return auctionMapper.toOngoingProductDtoList(ongoingAuctions);
    }

    @Transactional(readOnly = true)
    public List<EndedAuctionProductDto> getEndedAuctions(Member member) {
        List<Auction> endedAuctions = auctionRepository.findBySellerAndAuctionStatus(member, AuctionStatus.FINISHED);
        return auctionMapper.toEndedProductDtoList(endedAuctions);
    }

}

