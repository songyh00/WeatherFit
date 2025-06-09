import styled from 'styled-components';
import {getSeason, theme} from "../components/theme.js";
import {Link} from "react-router-dom";

const season = getSeason();
const currentTheme = theme[season];
export const LoginWrapper = styled.form`
    margin: 0 auto;
    width: 500px;
    text-align: center;
`;

export const LoginSection = styled.div`
    border-radius: 10px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
    border: 1px solid #ddd;
`;

export const LoginInput = styled.input`
    width: 464px;
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

export const LoginButton = styled.button`
    width: 464px;
    padding: 12px 15px;
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

export const LoginTexts = styled.div`
    margin: 5px auto 5px auto;
    width: 100%;
    text-align: center;
`;

export const JoinTheMembershipLink = styled(Link)`
    font-size: 12px;
    padding: 0 5px 0 0;
    text-decoration: none;
    color: #000;
`;
