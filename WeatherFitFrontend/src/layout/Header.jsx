import React, { useState, useEffect } from 'react';
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
import { Link, useNavigate } from 'react-router-dom';
import logo from "../assets/logo.png";

const Header = () => {
    const [activeMenu, setActiveMenu] = useState("");
    const [username, setUsername] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (!token) return;

        fetch("/api/user/profile", {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(res => {
                if (!res.ok) throw new Error("사용자 정보 불러오기 실패");
                return res.json();
            })
            .then(data => {
                setUsername(data.username);
            })
            .catch(err => {
                console.error("❌ 사용자 정보 불러오기 실패:", err);
            });
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        setUsername(null);
        navigate('/');
        window.location.reload(); // 새로고침
    };

    const onClickLogo = () => {
        setActiveMenu("");
    };

    return (
        <HeaderWrapper>
            <HeaderContent>
                <UserActionsContainer>
                    <MainLogoLink to="/" onClick={onClickLogo}>
                        <img src={logo} alt="WeatherFit Logo" />
                    </MainLogoLink>

                    <UserActions>
                        {username ? (
                            <>
                                <span style={{ fontSize: "11px" }}>{username}님</span>&nbsp;&nbsp;&nbsp;&nbsp;|
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
                    <MainMenuLink to="/Best" $active={activeMenu === "BEST"} onClick={() => setActiveMenu("BEST")}>BEST</MainMenuLink>
                    <MainMenuLink to="/Suggestion" $active={activeMenu === "추천"} onClick={() => setActiveMenu("추천")}>추천</MainMenuLink>
                    <MainMenuLink to="/Outerwear" $active={activeMenu === "아우터"} onClick={() => setActiveMenu("아우터")}>아우터</MainMenuLink>
                    <MainMenuLink to="/Consultation" $active={activeMenu === "상의"} onClick={() => setActiveMenu("상의")}>상의</MainMenuLink>
                    <MainMenuLink to="/Pants" $active={activeMenu === "하의"} onClick={() => setActiveMenu("하의")}>하의</MainMenuLink>
                </MainMenu>
            </HeaderNav>
        </HeaderWrapper>
    );
};

export default Header;
