import styled, {createGlobalStyle} from 'styled-components';

export const Container = styled.div`
    font-weight: bold;
    margin: 0 auto;
    width: 800px;
`;

export const Section = styled.section`
    margin-bottom: 70px;
    font-size: 16px;
`;

export const Title = styled.h2`
    font-size: 32px;
    color: #333;
    margin-top: 155px;
    line-height: 1.4;
    font-weight: bold;
    padding-bottom: 8px;
    text-align: center;
`;


export const SectionTitle = styled.h3`
    font-size: 28px;
    color: #333;
    font-weight: bold;
    margin: 30px 0;
    padding-bottom: 20px;

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

export const GlobalStyle = createGlobalStyle`
    .swiper-button-prev,
    .swiper-button-next {
        color: white;
    }
`;

export const GlobalStyleMainPage = createGlobalStyle`
    .swiper-button-prev,
    .swiper-button-next {
        color: white;
        width: 50px;      /* 버튼 가로 크기 */
        height: 50px;     /* 버튼 세로 크기 */
        border-radius: 25px;  /* 둥근 모서리 */
        top: 55%;
        transform: translateY(-50%);
    }

    .swiper-button-prev {
        left: 10px;
    }
    .swiper-button-next {
        right: 10px;
    }


    .swiper-button-prev::after,
    .swiper-button-next::after {
        font-size: 50px; 
        font-weight: bold;
    }
`;
