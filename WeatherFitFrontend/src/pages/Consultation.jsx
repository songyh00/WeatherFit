import React from 'react';
import '../App.css';
import {
    ContentsWrapper,
    MainContents,
    Card,
    Content,
    Like,
    ClothesText
} from "../layout/Best.style.js";
import logo from "../assets/logo.png";

const Consultation = () => {
    return (
        <ContentsWrapper>
            <MainContents>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            상의 1
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            상의 2
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            상의 3
                        </ClothesText>
                    </Content>
                </Card>



            </MainContents>
        </ContentsWrapper>
    );
}

export default Consultation;
