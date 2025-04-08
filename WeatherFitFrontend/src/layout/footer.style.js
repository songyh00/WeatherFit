import styled from 'styled-components';
import { Link } from 'react-router-dom';

export const FooterContainer = styled.div`
    background-color: white;
    padding: 25px 0;
    border-top: 1px solid darkgrey;
    .container {
        max-width: 1170px;
        margin: auto;
    }
    
`;

export const Row = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
`;

export const FooterCol = styled.div`
    width: 20%;
    padding: 0 15px;
    position: relative;

    &:not(:last-child) {
        border-right: 1px solid #ccc; /* 오른쪽 경계선 */
    }
    
    h4 {
        font-size: 18px;
        color: darkslategrey;
        text-transform: capitalize;
        margin-bottom: 20px;
        font-weight: 500;
        position: relative;
    }

    h4::before {
        content: '';
        position: absolute;
        left: 0;
        bottom: -10px;
        background-color: #292929;
        height: 2px;
        width: 50px;
    }
`;

export const FooterList = styled.ul`
    list-style: none;
    padding: 0;
    margin: 0;
`;

export const FooterItem = styled.li`
    margin-bottom: 10px;
    font-size: 14px; /* 텍스트 크기 */
    font-weight: 500; /* 글자 굵기 */
    color: #333;
`;

export const FooterLink = styled(Link)`
    font-size: 13px;
    color: #948f8f;
    text-decoration: none;
    font-weight: 300;
    display: block;
    transition: all 0.3s ease;

    &:hover {
        color: #383030;
        padding-left: 8px;
    }
`;
export const SocialLinks = styled.div`
    display: flex;
    gap: 10px;
`;

export const SocialLink = styled.a`
    display: inline-flex;
    align-items: center;
    justify-content: center;
    height: 40px;
    width: 40px;
    background-color: #fafaf7;
    border-radius: 50%;
    transition: all 0.5s ease;

    border: 1px solid black;

    &:hover {
        background-color: #e3e3e3;

        img {
            filter: brightness(0.8); /* hover 시 이미지 어둡게 */
        }
    }

    img {
        width: 23px;
        height: 23px;
        transition: filter 0.3s ease;
    }
`;
