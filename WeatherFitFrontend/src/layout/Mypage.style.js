import styled from 'styled-components';
import {Link} from "react-router-dom";

export const Container = styled.div`
    font-weight: bold;
    padding: 40px 20px;
    max-width: 800px;
    margin: 0 auto;

`;

export const Section = styled.section`
    margin-bottom: 100px;
    font-size: 16px;
    
`;

export const Title = styled.h2`
    font-size: 32px;
    color: #333;
    margin-top: 0px;
    line-height: 1.4;
    font-weight: bold;
    padding-bottom: 8px;
    text-align: center;
`;

export const InfoItem = styled.div`
    font-size: 20px;
    color: #333;
    margin-top: 8px;
    line-height: 1.4;
    font-weight: bold;
    padding-bottom:10px;
`;

export const StyledLink = styled(Link)`
    font-size: 20px;
    color: #0077cc;
    margin-top: 8px;
    line-height: 1.4;
    font-weight: bold;
    cursor: pointer;
    display: inline-block;
    padding-right: 30px;
    text-decoration: none;
`;



export const Emoji = styled.span`
    margin-right: 5px;
`;

export const Carousel = styled.div`
    display: flex;
    overflow-x: auto;
    gap: 14px;
    padding: 12px 0;
    scroll-snap-type: x mandatory;
    -webkit-overflow-scrolling: touch;
`;

export const Photo = styled.div`
    flex: 0 0 auto;
    width: 100px;
    height: 100px;
    background: #e0e0e0;
    border: 2px solid #aaa;
    display: flex;
    justify-content: center;
    align-items: center;
    scroll-snap-align: start;
    
`;

export const SectionTitle = styled.h3`
    font-size: 28px;
    color: #333;
    font-weight: bold;
    margin-bottom: 30px;
`;

export const CategoryTitle = styled.h4`
    font-size: 16px;
    color: #333;
    margin-top: 20px;
    line-height: 1.4;
    font-weight: bold;
    margin-bottom: 10px;
`;

export const Footer = styled.div`
    font-size: 20px;
    margin-top: 40px;
    color: #0077cc;
    line-height: 1.4;
    font-weight: bold;
    cursor: pointer;
    display: inline-block;
    text-decoration: none;
    
`;
