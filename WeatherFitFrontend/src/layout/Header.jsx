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


const Header = () => {
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

                    <HeaderNav>
                        <MainMenu>
                            {/* 임시 데이터 메뉴 */}
                            {/*<MainMenuLink to="/">오늘의 날씨</MainMenuLink> <- component로 하나 만들예정*/}
                            <MainMenuLink to="/Best">BEST</MainMenuLink>
                            <MainMenuLink to="/Suggestion">추천</MainMenuLink>
                            <MainMenuLink to="/Outerwear">아우터</MainMenuLink>
                            <MainMenuLink to="/Consultation">상의</MainMenuLink>
                            <MainMenuLink to="/Pants">바지</MainMenuLink>
                        </MainMenu>
                    </HeaderNav>
                </HeaderContent>
            </HeaderWrapper>
            <HeaderBackground />
        </>
    )
}

export default Header;