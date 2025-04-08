import React, { useState, useEffect } from 'react';
import '../App.css';
import {
    ContentsWrapper,
    ContentsTitle,
    Content,
    StyledImage,
    ServiceGrid,
    ServiceCard,
    ImageTextWrapper,
    LeftImage,
    RightText
} from "../layout/mainPage.style.js";
import Main1 from "../assets/spring.png";
import Main2 from "../assets/summer.png";
import Main3 from "../assets/autumn.png";
import Main4 from "../assets/winter.png";

import myImage1 from "../assets/img1.png";
import bestimg from "../assets/best2.png";
import recomimg from "../assets/recomimg.png";
import outerimg from "../assets/outerimg.png";
import clpaimg from "../assets/clpaimg.png";

const MainPage = () => {
    const images = [Main1, Main2, Main3, Main4];
    const [currentImage, setCurrentImage] = useState(0);

    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentImage((prevIndex) => (prevIndex + 1) % images.length);
        }, 8000);

        return () => clearInterval(interval);
    }, []);

    return (
        <ContentsWrapper style={{marginTop: '80px'}}>
            <Content>
                <StyledImage src={myImage1} alt="Image 1" />
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
