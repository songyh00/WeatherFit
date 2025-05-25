import React, { useState, useEffect } from 'react';
import {
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
    const [email, setEmail] = useState("");
    const [gender, setGender] = useState("");
    const [hoverKey, setHoverKey] = useState(0); // ✅ 드롭다운 리렌더링을 위한 key

    const navigate = useNavigate();
    const location = useLocation();

    const onClickLogo = () => {
        setActiveMenu("");
    };

    // 사용자 정보 요청
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
                console.log("✅ 서버에서 받은 성별 값:", data.gender);
                setUsername(data.username);
                setEmail(data.email);
                setGender(data.gender === "MALE" ? "남자" : data.gender === "FEMALE" ? "여자" : "기타");
            })
            .catch(err => {
                console.error("❌ 사용자 정보 불러오기 실패:", err);
            });
    }, [location.pathname]);

    useEffect(() => {
        const path = location.pathname.toLowerCase();
        if (path.includes('/best')) setActiveMenu("BEST");
        else if (path.includes('/suggestion')) setActiveMenu("추천");
        else if (path.includes('/outerwear')) setActiveMenu("아우터");
        else if (path.includes('/consultation')) setActiveMenu("상의");
        else if (path.includes('/pants')) setActiveMenu("하의");
        else setActiveMenu("");
    }, [location.pathname]);

    const closeDropdown = () => {
        setHoverKey(prev => prev + 1); // 강제로 리렌더링하여 hover 해제
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        setUsername(null);
        navigate('/');
        window.location.reload();
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
                                <ProfileContainer key={hoverKey}> {/* ✅ 드롭다운 닫기 위해 리렌더링 */}
                                    <ProfileImage>
                                        <DefaultProfile width={18} height={18} />
                                    </ProfileImage>&nbsp;
                                    <span style={{ fontSize: "11px" }}>
                                        {username}님
                                    </span>
                                    <div style={{
                                        display: 'flex',
                                        flexDirection: 'column',
                                        gap: '2px',
                                        alignItems: 'center',
                                        lineHeight: 1.2
                                    }}>
                                        <ProfileDropdown>
                                            <SvgWrapper style={{ margin: '20px auto 0 auto' }}>
                                                <ProfileImage>
                                                    <DefaultProfile />
                                                </ProfileImage>
                                            </SvgWrapper>

                                            <div style={{ fontSize: "18px", marginTop: '5px' }}>{username}</div>

                                            <div style={{
                                                fontSize: '12px',
                                                color: '#000',
                                                marginTop: '4px'
                                            }}>
                                                {email}
                                            </div>

                                            <div style={{
                                                fontSize: '12px',
                                                color: '#000',
                                                marginTop: '4px'
                                            }}>
                                                성별: {gender}
                                            </div>

                                            <div style={{ height: '10px' }} />

                                            <Link
                                                to="/MyPage"
                                                onClick={closeDropdown}
                                                style={{
                                                    textDecoration: 'none',
                                                    color: '#000',
                                                    fontSize: '12px',
                                                    lineHeight: '1.5'
                                                }}
                                            >
                                                마이페이지
                                            </Link><br />

                                            <Link
                                                to="/MyPageInfo"
                                                onClick={closeDropdown}
                                                style={{
                                                    textDecoration: 'none',
                                                    color: '#000',
                                                    fontSize: '12px',
                                                    lineHeight: '1.5'
                                                }}
                                            >
                                                내 정보 수정
                                            </Link><br />

                                            <Link
                                                to="/MyPagePassword"
                                                onClick={closeDropdown}
                                                style={{
                                                    textDecoration: 'none',
                                                    color: '#000',
                                                    fontSize: '12px',
                                                    lineHeight: '1.5'
                                                }}
                                            >
                                                비밀번호 변경
                                            </Link>

                                            <div
                                                onClick={() => {
                                                    handleLogout();
                                                    closeDropdown();
                                                }}
                                                style={{
                                                    cursor: 'pointer',
                                                    fontSize: "11px",
                                                    color: 'inherit',
                                                    textAlign: 'right',
                                                    margin: '12px 12px 8px 12px'
                                                }}
                                            >
                                                로그아웃
                                            </div>
                                        </ProfileDropdown>
                                    </div>
                                </ProfileContainer>&nbsp;&nbsp;|
                                <UserActionsLink to="/CustomerServiceCenter">고객센터</UserActionsLink>
                            </>
                        ) : (
                            <>
                                <UserActionsLink to="/Login">로그인</UserActionsLink> {"|"}
                                <UserActionsLink to="/JoinTheMembership">회원가입</UserActionsLink> {"|"}
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
