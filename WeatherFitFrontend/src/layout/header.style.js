import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';


export const HeaderWrapper = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    background: #fff;
    z-index: 1000;  
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); 
`;

export const HeaderContent = styled.div`
    width: 70%;
    margin: 0 auto;
    position: relative;
`;

export const UserActions = styled.div`
    display: flex;  // flex 사용 (가로 정렬사용하기위해)
    line-height: 30px;
    font-size: 10px;  
    color: #000;  
    justify-content: end;
    position: absolute;
    right: 0;
    top: 0;
`;

export const HeaderNav = styled.div`
    display: flex;
    align-items: center; /* 가운데 정렬 */
    justify-content: center;
    border-top : 1px solid darkgrey;
`;

export const UserActionsLink = styled(Link)`
    text-decoration: none;  // 링크의 기본 밑줄 제거
    color: #000; // 글자 색상 검은색으로 설정
    width: 60px;
    text-align: center;
    font-size: 11px;
`;

export const MainLogoLink = styled(Link)`
    text-decoration: none;  // 링크의 기본 밑줄 제거
    color: #000; // 글자 색상 검은색으로 설정
`;

export const MainMenu = styled.div`
    display: flex;
`;

export const MainMenuLink = styled(Link)`
    text-decoration: none;  // 링크의 기본 밑줄 제거
    color: #000; // 글자 색상 검은색으로 설정
    width: 150px;
    text-align: center;
    font-weight: bold;
    font-size: 16px;
    height: 50px;
    line-height: 50px;
    position: relative;

    &:hover {
        color: #939696;
    }

    &::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        height: 2px;
        width: ${({$active}) => ($active ? '100%' : '0')};
        background-color: #5c5e5e;
        transition: width 0.3s ease-in-out;
    } 
`;

export const UserActionsContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
`;

export const HeaderBackground = styled.div`
    background: #fff;
    width: 100%;
    height: 176px;
    z-index: 9;
    position: fixed;
`;

export const ProfileDropdown = styled.div`
    position: absolute;
    top: 30px;
    right: 0;
    width: 210px;
    border-radius: 15px;

    /* ✅ 반투명 + 흐림 효과 */
    background-color: rgba(255, 255, 255, 0.9);
    backdrop-filter: blur(10px);

    text-align: center;
    box-shadow:
            0 2px 4px rgba(0, 0, 0, 0.08),
            0 8px 16px rgba(0, 0, 0, 0.12);

    display: none;
    pointer-events: auto;
    z-index: 999;
`;

export const ProfileContainer = styled.div`
    display: flex;
    align-items: center;
    &:hover ${ProfileDropdown} {
        display: block;
    }
`;

export const ProfileImage = styled.div`
    padding: 2px;
    border-radius: 50%; 
    object-fit: cover;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
`;

export const SvgWrapper = styled.div`
    width: 100px;
    height: 100px;
    & > svg {
        width: 100%;
        height: 100%;
    }
`;

