import React from 'react';
import '../App.css';
import {
    ContentsWrapper,
    MainContents,
    Mainimage,
    MainContentsLink
} from "../layout/Best.style.js";
import logo from "../assets/logo.png";

const Outerwear = () => {
    return (
        <ContentsWrapper>
            <ContentsWrapper>
                <ContentsWrapper>
                    <MainContents>

                        <Mainimage>
                            <MainContentsLink to="/">
                                <img src={logo} alt="WeatherFit Logo" />
                            </MainContentsLink>
                        </Mainimage>

                        <Mainimage>
                            <MainContentsLink to="/">
                                <img src={logo} alt="WeatherFit Logo" />
                            </MainContentsLink>
                        </Mainimage>

                        <Mainimage>
                            <MainContentsLink to="/">
                                <img src={logo} alt="WeatherFit Logo" />
                            </MainContentsLink>
                        </Mainimage>

                    </MainContents>
                </ContentsWrapper>
            </ContentsWrapper>
        </ContentsWrapper>
    );
}

export default Outerwear;
