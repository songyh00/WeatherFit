import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';
import {getSeason, theme} from "../components/theme.js";

const season = getSeason();
const currentTheme = theme[season];

export const RegisterWrapper = styled.form`
    width: 500px;
    margin: 0 auto;
    text-align: center;
`;

export const DuplicateTestBtn = styled.button`
    width: 64px;
    margin: 10px 0 0 10px;
    font-size: 12px;
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

export const BtnSection = styled.div`
    display: flex;
    justify-content: center;
`;

export const CheckboxWrapper = styled.div`
    display: flex;
    width: 464px;
    margin: 10px auto 0;
    padding: 0 0 0 5px;
`;
