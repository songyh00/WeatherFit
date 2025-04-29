import React, { useState, useEffect } from 'react';
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
    RightText,
    SliderContainer
} from "../layout/mainPage.style.js";
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

const MainPage = () => {
    const images = [Main1, Main2, Main3, Main4];
    const [currentImage, setCurrentImage] = useState(0);
    const borderColors = [ // 계절별 이미지 테두리 색상 넣음
        "#fcb5b5", // 봄
        "#a1dffb", // 여름
        "#f9c981", // 가을
        "#d0e4ef"  // 겨울
    ];
    const sliderImages = [
        { src: Mainimg1, label: "봄",credit: "출처: 2-plan.co.kr"},
        { src: Mainimg2, label: "여름",credit: "출처: 2-plan.co.kr" },
        { src: Mainimg3, label: "가을",credit: "출처: 2-plan.co.kr" },
        { src: Mainimg4, label: "겨울",credit: "출처: 2-plan.co.kr" }
    ];

    const [sliderIndex, setSliderIndex] = useState(0);
    const totalSlides = images.length;
    const visibleSlides = 3;

    const goToPrev = () => {
        setSliderIndex((prevIndex) =>
            prevIndex <= 0 ? totalSlides - visibleSlides : prevIndex - 1
        );
    };

    const goToNext = () => {
        setSliderIndex((prevIndex) =>
            prevIndex >= totalSlides - visibleSlides ? 0 : prevIndex + 1
        );
    };



    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentImage((prevIndex) => (prevIndex + 1) % images.length);
        }, 8000);

        return () => clearInterval(interval);
    }, []);

    return (
        <ContentsWrapper>
            <Content>
                <IoIosArrowBack
                    size={45}
                    onClick={goToPrev}
                    className="arrow-button left"
                />
                <SliderContainer
                    $sliderIndex={sliderIndex}
                    $totalImages={sliderImages.length}
                >
                    {sliderImages.map((item, idx) => (
                        <StyledImageContainer key={idx}>
                            <StyledImage src={item.src} alt={`slide-${idx}`} />
                            <div className="slide-label">{item.label}</div>
                            <div className="image-credit">{item.credit}</div> {/* 👈 출처 표시 */}
                        </StyledImageContainer>
                    ))}
                </SliderContainer>
                <IoIosArrowForward
                    size={45}
                    onClick={goToNext}
                    className="arrow-button right"
                />
            </Content>
            <ContentsTitle>
                <h1>계절이 바뀔때 마다, 오늘 무슨 옷 입을지 고민되지 않으세요?</h1>

                <ImageTextWrapper> {/*이미지랑 테두리 색상 넣음*/}
                    <LeftImage $borderColor={borderColors[currentImage]}>
                        <img src={images[currentImage]} alt="Main Slide" />
                    </LeftImage>
                    <RightText>
                        <p>
                            매일 반복되는 "뭐 입지?" 고민,<br /><br />
                            <strong>날씨에</strong> 딱 맞는 옷차림을 추천해드립니다.  <br />
                            <strong style={{ color: "#ab618c" }}>봄</strong>·
                            <strong style={{ color: "#f28b00" }}>여름</strong>·
                            <strong style={{ color: "#5e2309" }}>가을</strong>·
                            <strong style={{ color: "#6c7b80" }}>겨울</strong>,
                            그리고 오늘 날씨까지 고려한 <br />
                            맞춤형 코디를 한눈에 확인해보세요!
                        </p>
                    </RightText>
                </ImageTextWrapper>

                <h1>
                    지금 계절또는 오늘의 날씨에 맞는 BEST 옷 추천부터<br/>
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
                    <ServiceCard to="Consultation">
                        <h2>상의 & 하의</h2>
                        <img src={clpaimg} alt="Top & Bottom" />
                    </ServiceCard>
                </ServiceGrid>
            </ContentsTitle>
        </ContentsWrapper>
    );
};

export default MainPage;
