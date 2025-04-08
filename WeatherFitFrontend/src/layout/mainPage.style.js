import styled from 'styled-components';
import React from 'react';
import {Link} from "react-router-dom";

export const ContentsWrapper = styled.div`
    padding: 176px 0 0 0;
    width: 80%;
    margin: 30px auto 0 auto;
`;

export const Content = styled.div`
    position: relative;
    overflow: hidden;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    .arrow-button {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        z-index: 2;
        cursor: pointer;
        color: white;
        transition: color 0.3s ease;
    }

    .left {
        left: 10px;
        
    }

    .right {
        right: 10px;
    }

    `;

export const SliderContainer = styled.div`
    display: flex;
    transition: transform 0.5s ease-in-out;
    transform: ${({ sliderIndex }) => `translateX(-${sliderIndex * (100 / 3)}%)`};
    width: ${({ totalImages }) => `${totalImages * (100 / 3)}%`};
`;
export const StyledImageContainer = styled.div`
    flex: 0 0 calc(100% / 3); // 화면 너비의 1/3만큼씩 차지
    padding: 0 10px; // 좌우 여백
    box-sizing: border-box;
`;

export const StyledImage = styled.img`
    width: 100%;
    height: 600px;
    object-fit: cover;
    border-radius: 5px;
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
        border-radius: 120px;
    }
`;

export const RightText = styled.div`
    flex: 0.9;
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
