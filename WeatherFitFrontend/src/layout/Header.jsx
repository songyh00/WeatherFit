import React from 'react';
import {
    HeaderContent,
    HeaderNav,
    HeaderWrapper,
    MainLogoLink,
    MainLogoTitle,
    MainMenu,
    MainMenuLink,
    UserActions,
    UserActionsLink
} from './index.style.js';
import { Link } from 'react-router-dom';
import MainLogo from "../components/MainLogo.jsx";
import logo from "../assets/logo.png";


const Header = () => {
    return (
        // styled-components, react-router-dom을 사용하고 있습니다. 의존성 설치를 위해 'npm install'을 실행해주세요.
        // styled 코드들은 src/layout/index.style.js 파일에 있습니다.
        <HeaderWrapper>
            <HeaderContent>
                <UserActions>
                    {/* 추후 라우팅 추가 예정 */}
                    <UserActionsLink to="/">로그인</UserActionsLink> |&nbsp;
                    <UserActionsLink to="/">회원가입</UserActionsLink> |&nbsp;
                    <UserActionsLink to="/">마이페이지</UserActionsLink> |&nbsp;
                    <UserActionsLink to="/">고객센터</UserActionsLink>

                </UserActions>


                <HeaderNav>
                    <MainLogoLink to="/">
                        {/* MainLogo 컴포넌트와 로고 텍스트 */}
                        {/* <MainLogo /> */}
                        <img src={logo} alt="WeatherFit Logo" />
                    </MainLogoLink>

                    <MainMenu>
                        {/* 임시 데이터 메뉴 */}
                        <MainMenuLink to="/">BEST</MainMenuLink>
                        <MainMenuLink to="/">오늘의 날씨</MainMenuLink>
                        <MainMenuLink to="/">추천</MainMenuLink>
                        <MainMenuLink to="/">아우터</MainMenuLink>
                        <MainMenuLink to="/">상의</MainMenuLink>
                        <MainMenuLink to="/">바지</MainMenuLink>
                    </MainMenu>

                </HeaderNav>

            </HeaderContent>


        </HeaderWrapper>
    )
}

export default Header;