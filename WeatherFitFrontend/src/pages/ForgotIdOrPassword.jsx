import {Link, useSearchParams} from "react-router-dom";
import {ContentsWrapper} from "../layout/mainPage.style.js";
import logo from "../assets/logo.png";
import {
    JoinTheMembershipLink,
    LoginButton,
    LoginInput,
    LoginSection,
    LoginTexts,
    LoginWrapper
} from "../layout/login.style.js";
import {theme} from "../components/theme.js";
import React from "react";

const FindId = () => {
    return(
        <>
            <LoginWrapper>
                <Link to="/">
                    <img src={logo} alt="WeatherFit Logo" />
                </Link>
                <LoginSection>
                    아이디 찾기
                </LoginSection>
            </LoginWrapper>
        </>
    );
}
const FindPw = () => {
    return(
        <>
            <LoginWrapper>
                <Link to="/">
                    <img src={logo} alt="WeatherFit Logo" />
                </Link>
                <LoginSection>
                    비밀번호 찾기
                </LoginSection>
            </LoginWrapper>
        </>
    );
}

const ForgotIdOrPassword = () => {
    const [searchParams] = useSearchParams();
    const page = searchParams.get("page");

    return (
        <>
            {page === "id" && <FindId />}
            {page === "pw" && <FindPw />}
        </>
    );
};

export default ForgotIdOrPassword;