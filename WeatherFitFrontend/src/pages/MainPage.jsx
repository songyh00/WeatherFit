import React, { useRef, useState, useEffect } from 'react';
import { IoIosArrowForward, IoIosArrowBack } from "react-icons/io";
import '../App.css';
import {
    ContentsWrapper,
    ContentsTitle,
    Content,
    StyledImageContainer,
    StyledImage,
    ServiceGrid,
    ServiceCard,
    ImageTextWrapper,
    LeftImage,
    RightText, LeftImg, StrongWeather,
} from "../layout/mainPage.style.js";
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import { Navigation } from 'swiper/modules';

import Main1 from "../assets/WeatherImg/spring.png";
import Main2 from "../assets/WeatherImg/summer.png";
import Main3 from "../assets/WeatherImg/autumn.png";
import Main4 from "../assets/WeatherImg/winter.png";

import Mainimg1 from "../assets/mainimg/SpringMainImg.png";
import Mainimg2 from "../assets/mainimg/SummerMainImg.png";
import Mainimg3 from "../assets/mainimg/AutumnMainImg.png";
import Mainimg4 from "../assets/mainimg/WinterMainImg.png";

import bestimg from "../assets/infoImg/best.png";
import recomimg from "../assets/infoImg/recomimg.png";
import outerimg from "../assets/infoImg/outerimg.png";
import clpaimg from "../assets/infoImg/clpaimg.png";
import pants from "../assets/infoImg/pants.jpg";

import {MyCaretLeftRectangle, MyCaretRightRectangle} from "../components/SliderBtns.jsx";
import {getSeason, theme} from "../components/theme.js";

const MainPage = () => {
    const images = [Main1, Main2, Main3, Main4];
    const [currentImage, setCurrentImage] = useState(0);
    // const borderColors = [
    //     "#fcb5b5", // 봄
    //     "#a1dffb", // 여름
    //     "#f9c981", // 가을
    //     "#d0e4ef"  // 겨울
    // ];
    const [season, setSeason] = useState(getSeason());
    const sliderImages = [
        { src: Mainimg1, label: "봄", credit: "출처: 2-plan.co.kr" },
        { src: Mainimg2, label: "여름", credit: "출처: 2-plan.co.kr" },
        { src: Mainimg3, label: "가을", credit: "출처: 2-plan.co.kr" },
        { src: Mainimg4, label: "겨울", credit: "출처: 2-plan.co.kr" }
    ];

    const prevRef = useRef(null);
    const nextRef = useRef(null);

    const [swiperReady, setSwiperReady] = useState(false);
    useEffect(() => {
        setSwiperReady(true);
    }, []);

    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24); // 하루마다 갱신

        return () => clearInterval(interval);
    }, []);
    useEffect(() => {
        const seasons = ['spring', 'summer', 'autumn', 'winter'];
        let index = seasons.indexOf(getSeason());

        const seasonInterval = setInterval(() => {
            index = (index + 1) % seasons.length;
            setSeason(seasons[index]);
        }, 5000); // 10초마다 바뀜

        return () => clearInterval(seasonInterval);
    }, []);
    useEffect(() => {
        const seasons = ['spring', 'summer', 'autumn', 'winter'];
        const index = seasons.indexOf(season);
        setCurrentImage(index);
    }, [season]);


    return (
        <ContentsWrapper style={{ width: '70%' }}>
            <Content>
                {/* 좌측 아이콘 */}
                <div ref={prevRef} className="arrow-button left">
                    <MyCaretLeftRectangle className="custom-prev" />
                </div>


                {swiperReady && (
                    <Swiper
                        modules={[Navigation]}
                        navigation={{
                            prevEl: prevRef.current,
                            nextEl: nextRef.current,
                        }}
                        onBeforeInit={(swiper) => {
                            swiper.params.navigation.prevEl = prevRef.current;
                            swiper.params.navigation.nextEl = nextRef.current;
                        }}
                        loop={true}
                        slidesPerView={3}
                        spaceBetween={10}
                    >
                        {sliderImages.map((item, idx) => (
                            <SwiperSlide key={idx}>
                                <StyledImageContainer>
                                    <StyledImage src={item.src} alt={`slide-${idx}`} />
                                    <div className="slide-label">{item.label}</div>
                                    <div className="image-credit">{item.credit}</div>
                                </StyledImageContainer>
                            </SwiperSlide>
                        ))}
                    </Swiper>
                )}

                {/* 우측 아이콘 */}
                <div ref={nextRef} className="arrow-button right">
                    <MyCaretRightRectangle className="custom-next" />
                </div>
            </Content>

            <ContentsTitle>
                <h1>계절이 바뀔때 마다, 오늘 무슨 옷 입을지 고민되지 않으세요?</h1>

                <ImageTextWrapper>
                    <LeftImage>
                        <LeftImg
                            $borderColor={theme[season].borderColor}
                            src={images[currentImage]}
                            alt="Main Slide"
                        />
                    </LeftImage>
                    <RightText>
                        <p>
                            매일 반복되는 "뭐 입지?" 고민,<br /><br />
                            <strong>날씨에</strong> 딱 맞는 옷차림을 추천해드립니다.  <br />
                            <StrongWeather $borderColor={theme[season].borderColor}>봄</StrongWeather>·
                            <StrongWeather $borderColor={theme[season].borderColor}>여름</StrongWeather>·
                            <StrongWeather $borderColor={theme[season].borderColor}>가을</StrongWeather>·
                            <StrongWeather $borderColor={theme[season].borderColor}>겨울</StrongWeather>,
                            그리고 오늘 날씨까지 고려한 <br />
                            맞춤형 코디를 한눈에 확인해보세요!
                        </p>
                    </RightText>
                </ImageTextWrapper>

                <h1>
                    지금 계절또는 오늘의 날씨에 맞는 BEST 옷 추천부터<br />
                    상의, 하의, 아우터까지 대신 골라드립니다!
                </h1>

                <ServiceGrid>
                    <ServiceCard to="/Best">
                        <h2>BEST</h2>
                        <img src={bestimg} alt="Best" />
                    </ServiceCard>
                    <ServiceCard to="/Suggestion">
                        <h2>추천</h2>
                        <img src={recomimg} alt="Suggestion" />
                    </ServiceCard>
                    <ServiceCard to="/Outerwear">
                        <h2>아우터</h2>
                        <img src={outerimg} alt="Outer" />
                    </ServiceCard>
                    <ServiceCard to="/Consultation">
                        <h2>상의</h2>
                        <img src={clpaimg} alt="Top & Bottom" />
                    </ServiceCard>
                    <ServiceCard to="/Pants">
                        <h2>하의</h2>
                        <img src={pants} alt="Pants" />
                    </ServiceCard>
                </ServiceGrid>
            </ContentsTitle>
        </ContentsWrapper>
    );
};

export default MainPage;
