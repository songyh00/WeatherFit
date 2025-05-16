import React, { useState } from 'react';
import {
    HeaderBackground,
    HeaderContent,
    HeaderNav,
    HeaderWrapper,
    MainLogoLink,
    MainMenu,
    MainMenuLink, ProfileContainer, ProfileDropdown, ProfileImage, SvgWrapper,
    UserActions,
    UserActionsContainer,
    UserActionsLink
} from './header.style.js';
import { 
    Link, 
    useNavigate
} from 'react-router-dom'; // 🔥 useNavigate 추가
import logo from "../assets/logo.png";
import DefaultProfile from "../components/DefaultProfile.jsx";

const Header = () => {
    const [activeMenu, setActiveMenu] = useState("");
    function  onClickLogo() {
        setActiveMenu("");
    }
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
                        <MainLogoLink to="/" onClick={onClickLogo}>
                            <img src={logo} alt="WeatherFit Logo" />
                        </MainLogoLink>

                        <UserActions>
                            {username ? (
                                <>
                                    <ProfileContainer>
                                        <ProfileImage>
                                            {/* 기본프로필 */}
                                            <DefaultProfile width={18} height={18} />
                                        </ProfileImage>&nbsp;
                                        <span style={{fontSize: "11px"}}>
                                            {username}님
                                        </span>
                                        <ProfileDropdown>
                                            <SvgWrapper style={{margin: '20px auto 0 auto'}}>
                                                <ProfileImage>
                                                    <DefaultProfile/>
                                                </ProfileImage>
                                            </SvgWrapper>

                                            <div style={{fontSize: "18px"}}>
                                                {username}
                                            </div>

                                            <Link
                                                to="/MyPage"
                                                style={{
                                                    textDecoration: 'none',
                                                    color: '#000',
                                                    fontSize: '12px'
                                                }}
                                            >
                                                마이페이지
                                            </Link>

                                            {/* 로그아웃 버튼 */}
                                            <div
                                                onClick={handleLogout}
                                                style={{
                                                    cursor: 'pointer',
                                                    fontSize: "11px",
                                                    textDecoration: 'none',
                                                    color: 'inherit',
                                                    marginLeft: '12px',
                                                    marginRight: '12px',
                                                    textAlign: 'right'
                                                }}
                                            >
                                                로그아웃
                                            </div>
                                        </ProfileDropdown>
                                    </ProfileContainer>&nbsp;&nbsp;|
                                    <UserActionsLink to="/CustomerServiceCenter">고객센터</UserActionsLink>
                                </>
                            ) : (
                                <>
                                    <UserActionsLink onClick={onClickLogo} to="/CustomerServiceCenter">고객센터</UserActionsLink>|
                                    <UserActionsLink to="/Login">로그인</UserActionsLink>|
                                    <UserActionsLink to="/JoinTheMembership">회원가입</UserActionsLink>
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

                        <MainMenuLink to="/Pants" $active={activeMenu === "하의"}
                                      onClick={() => setActiveMenu("하의")}>하의</MainMenuLink>
                    </MainMenu>
                </HeaderNav>
            </HeaderWrapper>
        </>
    );
};

export default Header;
