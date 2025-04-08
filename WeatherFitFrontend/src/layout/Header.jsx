import React from 'react';
import {
    HeaderBackground,
    HeaderContent,
    HeaderNav,
    HeaderWrapper,
    MainLogoLink,
    MainMenu,
    MainMenuLink,
    UserActions,
    UserActionsContainer,
    UserActionsLink
} from './header.style.js';
import { Link } from 'react-router-dom';
import MainLogo from "../components/MainLogo.jsx";
import logo from "../assets/logo.png";
import {useState} from "react";


const Header = () => {
    const [activeMenu, setActiveMenu] = useState("");

    return (
        <>
            <HeaderWrapper>
                <HeaderContent>
                    <UserActionsContainer>
                        <MainLogoLink to="/">
                            <img src={logo} alt="WeatherFit Logo" />
                        </MainLogoLink>

                        <UserActions>
                            {/* 추후 라우팅 추가 예정 */}
                            <UserActionsLink to="/Login">로그인</UserActionsLink>|
                            <UserActionsLink to="/JoinTheMembership">회원가입</UserActionsLink>|
                            <UserActionsLink to="/MyPage">&nbsp;마이페이지</UserActionsLink>&nbsp;|
                            <UserActionsLink to="/CustomerServiceCenter">고객센터</UserActionsLink>
                        </UserActions>
                    </UserActionsContainer>


                </HeaderContent>
                <HeaderNav>
                    <MainMenu>
                        {/* 임시 데이터 메뉴 */}
                        {/*<MainMenuLink to="/">오늘의 날씨</MainMenuLink> <- component로 하나 만들예정*/}
                        <MainMenuLink to="/Best"  $active={activeMenu === "BEST"}
                                      onClick={() => setActiveMenu("BEST")}>BEST</MainMenuLink>

                        <MainMenuLink to="/Suggestion" $active={activeMenu === "추천"}
                                      onClick={() => setActiveMenu("추천")}>추천</MainMenuLink>

                        <MainMenuLink to="/Outerwear" $active={activeMenu === "아우터"}
                                      onClick={() => setActiveMenu("아우터")}>아우터</MainMenuLink>

                        <MainMenuLink to="/Consultation" $active={activeMenu === "상의"}
                                      onClick={() => setActiveMenu("상의")}>상의</MainMenuLink>

                        <MainMenuLink to="/Pants" $active={activeMenu === "바지"}
                                      onClick={() => setActiveMenu("바지")}>바지</MainMenuLink>
                    </MainMenu>
                </HeaderNav>
            </HeaderWrapper>
            <HeaderBackground />
        </>
    )
}

export default Header;