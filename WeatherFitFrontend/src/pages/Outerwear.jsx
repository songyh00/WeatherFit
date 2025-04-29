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

const Outerwear = () => {
    return (
        <ContentsWrapper>
            <MainContents>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            아우터 1
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            아우터 2
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            아우터 3
                        </ClothesText>
                    </Content>
                </Card>

            </MainContents>
        </ContentsWrapper>
    );
}

export default Outerwear;
