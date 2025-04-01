import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';


export const RegisterWrapper = styled.form`
    width: 500px;
    margin: 100px auto 0 auto;
    text-align: center;
`;

export const DuplicateTestBtn = styled.button`
    width: 64px;
    margin: 10px 0 0 10px;
    font-size: 12px;
    border: 2px solid ${(props) => props.borderColor};
    //border-style: none;
    background-color: ${(props) => props.borderColor};
    color: ${(props) => props.textColor || "#fff"};
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s ease-in-out;

    &:hover {
        background-color: ${(props) => props.hoverBgColor || props.bgColor};
    }

    &:focus {
        border-color: ${(props) => props.focusColor};
        outline: none;
        box-shadow: 0 0 8px rgba(74, 144, 226, 0.3);
    }
`;

export const BtnSection = styled.div`
    display: flex;
    justify-content: center;
`;



