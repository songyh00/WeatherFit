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

    &.contact {
        border-left: 1px solid #ccc; /* ← 세로 구분선 */
        padding-left: 25px; /* 선 때문에 살짝 더 여백 줘도 좋음 */
        margin-left: -1px; /* 선이 너무 안쪽으로 들어가는 느낌이면 살짝 땡겨줌 */
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
        color: #4d4242;
        padding-left: 8px;
    }
`;

export const SocialLinks = styled.div`
    display: flex;
    gap: 10px;
`;

export const SocialLink = styled.a`
    display: inline-block;
    height: 40px;
    width: 40px;
    font-size: 20px;
    background-color: #b3aaaa;
    text-align: center;
    line-height: 40px;
    border-radius: 50%;
    color: #b3aaaa;
    transition: all 0.5s ease;

    &:hover {
        color: #948f8f;
        background-color: #4d4242;
    }
`;