import React, { useState, useEffect } from 'react';
import {
    HeaderBackground,
    HeaderContent,
    HeaderNav,
    HeaderWrapper,
    MainLogoLink,
    MainMenu,
    MainMenuLink,
    ProfileContainer,
    ProfileDropdown,
    ProfileImage,
    SvgWrapper,
    UserActions,
    UserActionsContainer,
    UserActionsLink
} from './header.style.js';
import {
    Link,
    useLocation,
    useNavigate
} from 'react-router-dom';
import logo from "../assets/logo.png";
import DefaultProfile from "../components/DefaultProfile.jsx";

const Header = () => {
    const [activeMenu, setActiveMenu] = useState("");
    const [username, setUsername] = useState(null);
    const navigate = useNavigate();
    const location = useLocation();

    // 🔹 로고 클릭 시 메뉴 초기화
    const onClickLogo = () => {
        setActiveMenu("");
    };

    // 🔹 현재 URL 기준 자동 메뉴 활성화
    useEffect(() => {
        const path = location.pathname.toLowerCase();
        if (path.includes('/best')) setActiveMenu("BEST");
        else if (path.includes('/suggestion')) setActiveMenu("추천");
        else if (path.includes('/outerwear')) setActiveMenu("아우터");
        else if (path.includes('/consultation')) setActiveMenu("상의");
        else if (path.includes('/pants')) setActiveMenu("하의");
        else setActiveMenu("");
    }, [location.pathname]);

    // 🔹 사용자 정보 불러오기
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
        window.location.reload();
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
                                            <DefaultProfile width={18} height={18} />
                                        </ProfileImage>&nbsp;
                                        <span style={{ fontSize: "11px" }}>
                                            {username}님
                                        </span>
                                        <ProfileDropdown>
                                            <SvgWrapper style={{ margin: '20px auto 0 auto' }}>
                                                <ProfileImage>
                                                    <DefaultProfile />
                                                </ProfileImage>
                                            </SvgWrapper>

                                            <div style={{ fontSize: "18px" }}>
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
                                    <UserActionsLink to="/CustomerServiceCenter">고객센터</UserActionsLink>|
                                    <UserActionsLink to="/Login">로그인</UserActionsLink>|
                                    <UserActionsLink to="/JoinTheMembership">회원가입</UserActionsLink>
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
        </>
    );
};

export default Header;
