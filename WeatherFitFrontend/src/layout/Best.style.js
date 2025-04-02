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
    padding: 200px 20px;
`;



export const Mainimage = styled.div`
    width: 200px;
    height: 150px;
    border: 5px solid transparent;
    box-sizing: border-box;
    transition: transform .1s, background-color .6s;
    margin: 50px;
`;




export const MainContentsLink = styled.div`
    width: 100%;
    height: 100%;
    background-size: cover;
    background-position: center;
    border-radius: 15px;
    &:hover {
        transform: scale(1.2);
        z-index: 1;
        border-radius: 20px;
    }
`;