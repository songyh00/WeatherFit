import styled from 'styled-components';
import React from 'react';
import {Link} from "react-router-dom";
import {getSeason, theme} from "../components/theme.js";

const season = getSeason();
const currentTheme = theme[season];

export const SearchContainer = styled.div`
    display: flex;
    justify-content: center;
    margin: 20px 0 25px 0;
`;

export const SearchInput = styled.input`
    padding: 10px 15px;
    width: 315px;
    border: 2px solid ${currentTheme.borderColor};
    background-color: ${currentTheme.bgColor};
    outline: none;
    border-radius: 8px;
    font-size: 16px;
    
    &:focus {
        border-color: ${currentTheme.focusColor};
    }
`;

export const SearchButton = styled.button`
    padding: 10px 15px;
    border: 2px solid ${currentTheme.borderColor};
    background-color: ${currentTheme.borderColor};
    color: white;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
    
    
    &:hover {
        background-color: ${currentTheme.focusColor};
    }

    &:focus {
        border-color: ${currentTheme.focusColor};
        outline: none;
        box-shadow: 0 0 8px rgba(74, 144, 226, 0.3);
    }
`;