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
import { Link, useNavigate } from 'react-router-dom'; // useNavigate 추가
import logo from "../assets/logo.png";

const Header = () => {
    const navigate = useNavigate();
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true'; // 로그인 여부 확인

    const handleLogout = () => {
        localStorage.removeItem('isLoggedIn'); // 로그인 상태 삭제
        alert('로그아웃 되었습니다.');
        navigate('/Login'); // 로그인 페이지로 이동
    };

    return (
        <>
            <HeaderWrapper>
                <HeaderContent>
                    <UserActionsContainer>
                        <MainLogoLink to="/">
                            <img src={logo} alt="WeatherFit Logo" />
                        </MainLogoLink>

                        <UserActions>
                            {isLoggedIn ? (
                                // 로그인 되어 있으면 로그아웃 버튼
                                <UserActionsLink as="button" onClick={handleLogout} style={{ background: 'none', border: 'none', cursor: 'pointer' }}>
                                    로그아웃
                                </UserActionsLink>
                            ) : (
                                // 로그인 안 되어 있으면 로그인/회원가입
                                <>
                                    <UserActionsLink to="/Login">로그인</UserActionsLink>|
                                    <UserActionsLink to="/JoinTheMembership">회원가입</UserActionsLink>|
                                </>
                            )}
                            <UserActionsLink to="/MyPage">&nbsp;마이페이지</UserActionsLink>&nbsp;|
                            <UserActionsLink to="/CustomerServiceCenter">고객센터</UserActionsLink>
                        </UserActions>
                    </UserActionsContainer>

                    <HeaderNav>
                        <MainMenu>
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
    );
}

export default Header;
