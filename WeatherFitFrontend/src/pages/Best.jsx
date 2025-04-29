import React, { useState, useEffect } from "react";

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

const Best = () => {

    return (
        <ContentsWrapper>
            <MainContents>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            레트로 체크자켓
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            풀오버 후드티
                        </ClothesText>
                    </Content>
                </Card>

                <Card>
                    <img src={logo} alt="WeatherFit Logo" />
                    <Content>
                        <Like>찜하기  ♥</Like>
                        <ClothesText>
                            빈티지 청바지
                        </ClothesText>
                    </Content>
                </Card>


            </MainContents>
        </ContentsWrapper>
    );
}

export default Best;
