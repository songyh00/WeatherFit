import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';

export const ContentsWrapper = styled.div`


`;

export const MainContents = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    max-width: 1400px;
    margin: 0 auto;
    padding-bottom: 20px;

`;


export const Wimg = styled.div`
    width: 100%;
    height: 420px; /* 고정 이미지 영역 */
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;


    img {
        max-height: 100%;
        max-width: 100%;
        object-fit: contain;
        display: block;
    }
`;


export const Card = styled.div`

    box-sizing: border-box;
    transition: transform .1s, background-color .6s;
    margin: 50px;
    text-align: center;
    width: 350px;
    height: 500px; // 카드 크기
    border: 1px solid #e0e0e0;
    border-radius: 10px;
    overflow: hidden;
    font-family: Arial, sans-serif;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);




    &:hover {
        transform: scale(1.1);
    }

`;



export const Content = styled.div`
    padding: 16px;

`;

export const Like = styled.div`
    color: #ff0000;
    font-size: 20px;
    cursor: pointer;

`;


export const ClothesText = styled.p`
  font-size: 14px;
  color: #333;
  line-height: 1.4;
`;