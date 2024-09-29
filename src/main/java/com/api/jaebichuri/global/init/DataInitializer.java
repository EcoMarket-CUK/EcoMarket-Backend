package com.api.jaebichuri.global.init;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.entity.AuctionBid;
import com.api.jaebichuri.auction.entity.AuctionProduct;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.auction.repository.BidRepository;
import com.api.jaebichuri.auction.repository.ProductRepository;

import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.member.enums.Role;
import com.api.jaebichuri.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;
    private final BidRepository bidRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Member member1 = Member.builder()
                .clientId("12345:KAKAO")
                .nickname("seller")
                .role(Role.USER)
                .build();

        Member member2 = Member.builder()
                .clientId("23456:KAKAO")
                .nickname("bidder")
                .role(Role.USER)
                .build();

        Member member3 = Member.builder()
                .clientId("34567:KAKAO")
                .nickname("bidder")
                .role(Role.USER)
                .build();

        Member member4 = Member.builder()
                .clientId("45678:KAKAO")
                .nickname("bidder")
                .role(Role.USER)
                .build();

        Member member5 = Member.builder()
                .clientId("56789:KAKAO")
                .nickname("bidder")
                .role(Role.USER)
                .build();

        Auction auction1 = Auction.builder()
                .startPrice(10000)
                .startTime(LocalDateTime.of(2024, 10, 10, 12, 30, 30))
                .endTime(LocalDateTime.of(2024, 10, 11, 12, 30, 30))
                .auctionStatus(AuctionStatus.UPCOMING)
                .seller(member1)
                .build();

        Auction auction2 = Auction.builder()
                .startPrice(30000)
                .startTime(LocalDateTime.of(2024, 10, 15, 12, 30, 30))
                .endTime(LocalDateTime.of(2024, 10, 18, 12, 30, 30))
                .auctionStatus(AuctionStatus.UPCOMING)
                .seller(member1)
                .build();

        Auction auction3 = Auction.builder()
                .startPrice(5000)
                .startTime(LocalDateTime.of(2024, 10, 10, 12, 30, 30))
                .endTime(LocalDateTime.of(2024, 10, 13, 12, 30, 30))
                .auctionStatus(AuctionStatus.ONGOING)
                .seller(member1)
                .build();

        AuctionProduct product1 = AuctionProduct.builder()
                .productName("Product 1")
                .productDescription("Product 1에 대한 설명")
                .build();

        AuctionProduct product2 = AuctionProduct.builder()
                .productName("Product 2")
                .productDescription("Product 2에 대한 설명")
                .build();

        AuctionProduct product3 = AuctionProduct.builder()
                .productName("Product 3")
                .productDescription("Product 3에 대한 설명")
                .build();

        AuctionBid bid1 = AuctionBid.builder()
                .bidPrice(11000)
                .bidTime(LocalDateTime.of(2024, 10, 11, 6, 30, 30))
                .auction(auction1)
                .bidder(member2)
                .build();

        AuctionBid bid2 = AuctionBid.builder()
                .bidPrice(12000)
                .bidTime(LocalDateTime.of(2024, 10, 11, 7, 30, 30))
                .auction(auction1)
                .bidder(member3)
                .build();

        AuctionBid bid3 = AuctionBid.builder()
                .bidPrice(13000)
                .bidTime(LocalDateTime.of(2024, 10, 11, 8, 30, 30))
                .auction(auction1)
                .bidder(member4)
                .build();

        AuctionBid bid4 = AuctionBid.builder()
                .bidPrice(20000)
                .bidTime(LocalDateTime.of(2024, 10, 11, 9, 30, 30))
                .auction(auction1)
                .bidder(member5)
                .build();

        AuctionBid bid5 = AuctionBid.builder()
                .bidPrice(25000)
                .bidTime(LocalDateTime.of(2024, 10, 11, 10, 30, 30))
                .auction(auction1)
                .bidder(member2)
                .build();

        AuctionBid bid6 = AuctionBid.builder()
                .bidPrice(50000)
                .bidTime(LocalDateTime.of(2024, 10, 16, 11, 30, 30))
                .auction(auction2)
                .bidder(member3)
                .build();

        AuctionBid bid7 = AuctionBid.builder()
                .bidPrice(51000)
                .bidTime(LocalDateTime.of(2024, 10, 17, 11, 30, 30))
                .auction(auction2)
                .bidder(member4)
                .build();

        AuctionBid bid8 = AuctionBid.builder()
                .bidPrice(50000)
                .bidTime(LocalDateTime.of(2024, 10, 11, 11, 30, 30))
                .auction(auction3)
                .bidder(member3)
                .build();

        AuctionBid bid9 = AuctionBid.builder()
                .bidPrice(55000)
                .bidTime(LocalDateTime.of(2024, 10, 12, 11, 30, 30))
                .auction(auction3)
                .bidder(member4)
                .build();

        AuctionBid bid10 = AuctionBid.builder()
                .bidPrice(58000)
                .bidTime(LocalDateTime.of(2024, 10, 13, 11, 30, 30))
                .auction(auction3)
                .bidder(member5)
                .build();

        auction1.setProduct(product1);
        auction2.setProduct(product2);
        auction3.setProduct(product3);

        memberRepository.saveAll(Arrays.asList(member1, member2, member3, member4, member5));
        productRepository.saveAll(Arrays.asList(product1, product2, product3));
        auctionRepository.saveAll(Arrays.asList(auction1, auction2, auction3));
        bidRepository.saveAll(Arrays.asList(bid1, bid2, bid3, bid4, bid5, bid6, bid7, bid8, bid9, bid10));
    }

}
