import styled from 'styled-components';
import React from 'react';
import { Link } from 'react-router-dom';

export const LoginWrapper = styled.form`
    margin: 100px auto;
    width: 500px;
    text-align: center;
`;

export const LoginSection = styled.div`
    border-radius: 10px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
    border: 1px solid #ddd;
`;

export const LoginInput = styled.input`
  width: 430px;
  padding: 12px 15px;
  margin: 10px 0 0 0;  
  font-size: 16px;
  border: 2px solid ${(props) => props.borderColor};
  background-color: ${(props) => props.bgColor};
  border-radius: 8px;
  transition: 0.3s ease-in-out;

  &:focus {
    border-color: ${(props) => props.focusColor};
    outline: none;
    box-shadow: 0 0 8px rgba(74, 144, 226, 0.3);
  }

  &::placeholder {
    color: #aaa;
    font-style: italic;
  }
`;

export const LoginButton = styled.button`
    width: 464px;
    padding: 12px 15px;
    margin: 30px 0 20px 0;
    font-size: 16px;
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
