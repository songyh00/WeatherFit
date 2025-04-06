import styled from 'styled-components';
import React from 'react';
import {Link} from "react-router-dom";

export const ContentsWrapper = styled.div`
    padding: 176px 0 0 0;
    width: 80%;
    margin: 0 auto;
`;

export const Content = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
`;

export const StyledImage = styled.img`
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
`;

export const ContentsTitle = styled.div`
    padding: 40px 40px;
    text-align: center;
    > h1 {
        padding: 40px 80px;
        font-family:'Noto Sans KR', sans-serif;
    }
`;

export const ImageTextWrapper = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 40px 0;

    @media (max-width: 768px) {
        flex-direction: column;
    }
`;

export const LeftImage = styled.div`
    flex: 1;
    padding-right: 30px;

    img {
        width: 65%;
        height: auto;
        border-radius: 2px;
    }
`;

export const RightText = styled.div`
    flex: 1;
    text-align: left;
    font-size: 1.4rem;
    line-height: 1.5;
    font-family: 'Noto Sans KR', sans-serif;
`;

export const ServiceGrid = styled.div`
    display: grid;
    grid-template-columns: repeat(4, 1fr); // 가로 4열
    gap: 40px;
    margin-top: 50px;
    padding: 0 40px;
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15); // 기본 그림자
    border-top: 1px solid #bfbfbf;
`;

export const ServiceCard = styled(Link)`
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    background-color: transparent;
    text-decoration: none;
    color: inherit;
    overflow: hidden;
    padding: 20px;

    img {
        max-width: 135%;
        height: auto;
        object-fit: contain;
        margin-bottom: 6px;
        transition: transform 0.3s ease, box-shadow 0.3s ease, filter 0.3s ease;

        &:hover {
            transform: scale(0.95);
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.4);
            filter: brightness(50%);
        }
    }

    h2 {
        position: absolute;
        top: 45%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-size: 17px;
        font-family: 'Noto Sans KR', sans-serif;
        color: white;
        font-weight: bold;
        pointer-events: none; // 텍스트가 클릭 방해 안되게
        z-index: 1;
        text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.9);
    }
`;
