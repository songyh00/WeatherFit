import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';

export const HeaderWrapper = styled.div`
    width: 100%;
    padding-bottom: 70px;
    
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
    font-size: 10px;  
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
    flex-direction: column; /* 세로 정렬 */
    align-items: center; /* 가운데 정렬 */
    height: 10px;
    justify-content: center;
    gap: 0px; /* 로고와 메뉴 사이 */
    padding-top: 10px; /* 전체적으로 아래로 내림 */
    
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
    font-size: 15px;
    height: 50px;
    line-height: 40px;
`;


