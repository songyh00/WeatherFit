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

const Header = () => {
    return (
        // styled-components, react-router-dom을 사용하고 있습니다. 의존성 설치를 위해 'npm install'을 실행해주세요.
        // styled 코드들은 src/layout/index.style.js 파일에 있습니다.
        <HeaderWrapper>
            <HeaderContent>
                <UserActions>
                    {/* 추후 라우팅 추가 예정 */}
                    <UserActionsLink to="/">이용안내</UserActionsLink> |&nbsp;
                    <UserActionsLink to="/">고객센터</UserActionsLink> |&nbsp;
                    <UserActionsLink to="/">로그인</UserActionsLink> |&nbsp;
                    <UserActionsLink to="/">회원가입</UserActionsLink>
                </UserActions>

                <HeaderNav>
                    <MainLogoLink to="/">
                        {/* MainLogo 컴포넌트와 로고 텍스트 */}
                        {<MainLogo />}
                        <MainLogoTitle>WeatherFit</MainLogoTitle>
                    </MainLogoLink>

                    <MainMenu>
                        {/* 임시 데이터 메뉴 */}
                        <MainMenuLink to="/">임시데이터1</MainMenuLink>
                        <MainMenuLink to="/">임시데이터2</MainMenuLink>
                        <MainMenuLink to="/">임시데이터3</MainMenuLink>
                        <MainMenuLink to="/">임시데이터4</MainMenuLink>
                        <MainMenuLink to="/">임시데이터5</MainMenuLink>
                    </MainMenu>
                </HeaderNav>
            </HeaderContent>


        </HeaderWrapper>
    )
}

export default Header;