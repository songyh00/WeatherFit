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
import Main1 from "../assets/spring.png";
import Main2 from "../assets/summer.png";
import Main3 from "../assets/autumn.png";
import Main4 from "../assets/winter.png";

import Mainimg1 from "../assets/mainimg/mainimg1.jpg";
import Mainimg2 from "../assets/mainimg/mainimg2.jpg";
import Mainimg3 from "../assets/mainimg/mainimg3.jpg";
import Mainimg4 from "../assets/mainimg/mainimg4.jpg";


import bestimg from "../assets/best2.png";
import recomimg from "../assets/recomimg.png";
import outerimg from "../assets/outerimg.png";
import clpaimg from "../assets/clpaimg.png";

const MainPage = () => {
    const images = [Main1, Main2, Main3, Main4];
    const [currentImage, setCurrentImage] = useState(0);
    const sliderImages = [Mainimg1, Mainimg2, Mainimg3, Mainimg4];
    const [sliderIndex, setSliderIndex] = useState(0);
    const totalSlides = images.length;
    const visibleSlides = 3;

    const goToPrev = () => {
        setSliderIndex((prevIndex) =>
            prevIndex === 0 ? totalSlides - visibleSlides : prevIndex - 1
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
                    sliderIndex={sliderIndex}
                    totalImages={sliderImages.length}
                >
                    {sliderImages.map((img, idx) => (
                        <StyledImageContainer key={idx}>
                            <StyledImage src={img} alt={`slide-${idx}`} />
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

                <ImageTextWrapper>
                    <LeftImage>
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
                    <ServiceCard>
                        <h2>상의 & 하의</h2>
                        <img src={clpaimg} alt="Top & Bottom" />
                    </ServiceCard>
                </ServiceGrid>
            </ContentsTitle>
        </ContentsWrapper>
    );
};

export default MainPage;
