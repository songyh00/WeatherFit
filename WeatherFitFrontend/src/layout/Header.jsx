import React, { useState } from 'react';
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
import { Link, useNavigate } from 'react-router-dom'; // 🔥 useNavigate 추가
import logo from "../assets/logo.png";

const Header = () => {
    const [activeMenu, setActiveMenu] = useState("");
    const username = localStorage.getItem('username'); // 🔥 로그인한 사용자 이름 가져오기
    const navigate = useNavigate(); // 🔥 페이지 이동용

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        navigate('/'); // 메인 페이지로 이동
        window.location.reload(); // 새로고침(헤더 다시 그림)
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
                            {username ? (
                                <>
                                    <span style={{fontSize: "11px"}}>
                                        {username}님</span>&nbsp;&nbsp;&nbsp;&nbsp;|

                                    <span
                                        onClick={handleLogout}
                                        style={{
                                            cursor: 'pointer',
                                            fontSize: "11px",
                                            textDecoration: 'none',
                                            color: 'inherit',
                                            marginLeft: '12px',
                                            marginRight: '12px'
                                        }}
                                    >
                                        로그아웃
                                    </span>
                                    |&nbsp;&nbsp;
                                    <UserActionsLink to="/MyPage">마이페이지</UserActionsLink>&nbsp;&nbsp;|
                                    <UserActionsLink to="/CustomerServiceCenter">고객센터</UserActionsLink>
                                </>
                            ) : (
                                <>
                                    <UserActionsLink to="/Login">로그인</UserActionsLink>|&nbsp;
                                    <UserActionsLink to="/JoinTheMembership">회원가입</UserActionsLink>&nbsp;|&nbsp;&nbsp;
                                    <UserActionsLink to="/MyPage">마이페이지</UserActionsLink>&nbsp;&nbsp;|
                                    <UserActionsLink to="/CustomerServiceCenter">고객센터</UserActionsLink>
                                </>
                            )}
                        </UserActions>
                    </UserActionsContainer>
                </HeaderContent>

                <HeaderNav>
                    <MainMenu>
                        <MainMenuLink to="/Best" $active={activeMenu === "BEST"}
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
    );
};

export default Header;
