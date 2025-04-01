import React from 'react';
import '../App.css';
import {
    ContentsWrapper
} from "../layout/mainPage.style.js";
import {LoginButton, LoginInput, LoginSection, LoginWrapper} from "../layout/login.style.js";
import {Link} from "react-router-dom";
import logo from "../assets/logo.png";

const JoinTheMembership = () => {
    return (
        <RegisterWrapper
            // action={} 어디로 전달해줘야할지 모르곘어서 일단 두겠습니다
        >
            <Link to="/">
                <img src={logo} alt="WeatherFit Logo" />
            </Link>
            <LoginSection>

            </LoginSection>
        </RegisterWrapper>
    );
}

export default JoinTheMembership;
