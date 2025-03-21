import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';

export const HeaderWrapper = styled.div`
    width: 100%;
    height: 150px;
`;

export const HeaderContent = styled.div`
    width: 60%;
    margin: 0 auto;
`;

export const UserActions = styled.div`
    display: flex;  // flex 사용 (가로 정렬사용하기위해)
    height: 30px;
    line-height: 30px;
    padding: 5px 0 30px 0;
    font-size: 14px;  
    color: #000;  
    gap: 10px;  // 아이템 사이 간격
    justify-content: end;
`;

export const MainLogoTitle = styled.span`
    font-size: 45px;
    padding: 0 0 0 10px;
`;

export const HeaderNav = styled.div`
    display: flex;
    align-items: center;
    height: 90px;
    justify-content: space-between;
`;

export const UserActionsLink = styled(Link)`
    text-decoration: none;  // 링크의 기본 밑줄 제거
    color: #000; // 글자 색상 검은색으로 설정
`;

export const MainLogoLink = styled(Link)`
    text-decoration: none;  // 링크의 기본 밑줄 제거
    color: #000; // 글자 색상 검은색으로 설정
    display: flex;
    align-items: center;
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
    font-size: 18px;
    height: 90px;
    line-height: 90px;
`;


