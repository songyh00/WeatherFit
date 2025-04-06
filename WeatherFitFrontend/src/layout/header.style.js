import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';


export const HeaderWrapper = styled.div`
    background: #fff;
    z-index: 10;
    position: relative;
`;

export const HeaderContent = styled.div`
    width: 80%;
    position: fixed;
    left: 50%;
    transform:translate(-50%, 0);
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
    box-shadow: 0px 7px 10px rgba(0, 0, 0, 0.1);
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


