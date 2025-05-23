import styled from 'styled-components';
import {getSeason, theme} from "../components/theme.js";

const season = getSeason();
const currentTheme = theme[season];

export const ContentsWrapper = styled.div`
    padding: 50px 0 70px 0;
    width: 40%;
    margin: 30px auto 0 auto;
    
`;

export const Container = styled.div`
    max-width: 960px;
    margin: 0 auto;
    padding: 2rem;
`;

export const Card = styled.div`
    background: #fff;
    padding: 1.5rem;
    border-radius: 12px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
`;

export const Title = styled.h2`
    margin-bottom: 1rem;
    font-size: 1.5rem;
    text-align: center;
`;


export const Input = styled.input`
    width: 100%;
    padding: 12px 15px;
    margin: 10px 0 0 0;
    font-size: 16px;
    border: 2px solid ${currentTheme.borderColor};
    background-color: ${currentTheme.bgColor};
    border-radius: 8px;
    transition: 0.3s ease-in-out;

    &:focus {
        border-color: ${currentTheme.focusColor};
        outline: none;
        box-shadow: 0 0 8px rgba(74, 144, 226, 0.3);
    }

    &::placeholder {
        color: #aaa;
        font-style: italic;
    }
`;

export const Button = styled.button`
    padding: 0.6rem 1.2rem;
    margin: 13px 0 0 0;
    font-size: 16px;
    border: 2px solid ${currentTheme.borderColor};
    background-color: ${currentTheme.borderColor};
    color: white;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s ease-in-out;

    &:hover {
        background-color: ${currentTheme.focusColor};
    }

    &:focus {
        border-color: ${currentTheme.focusColor};
        outline: none;
        box-shadow: 0 0 8px rgba(74, 144, 226, 0.3);
    }
`;