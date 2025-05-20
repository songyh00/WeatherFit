import styled from 'styled-components';
import { Link } from "react-router-dom";
import { IoIosArrowBack, IoIosArrowForward } from "react-icons/io";

export const ContentsWrapper = styled.div`
    width: 80%;
    margin: 0 auto;
`;

export const Content = styled.div`
    position: relative;
    overflow: hidden;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;

    .arrow-button {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        z-index: 2;
        cursor: pointer;
        color: white;
        transition: color 0.3s ease;
    }

    .left {
        left: 20px;
    }

    .right {
        right: 20px;
    }
`;

export const SliderContainer = styled.div`
    display: flex;
    transition: transform 0.5s ease-in-out;
    transform: ${({ sliderIndex }) => `translateX(-${sliderIndex * (100 / 3)}%)`};
    width: ${({ totalImages }) => `${totalImages * (100 / 3)}%`};
`;

export const StyledImageContainer = styled.div`
    position: relative;  // 텍스트를 겹치기 위해 추가
    flex: 0 0 calc(100% / 3); // 화면 너비의 1/3만큼씩 차지
    padding: 0 2px;
    box-sizing: border-box;

    .slide-label {
        position: absolute;
        bottom: 15px;
        right: 40px;
        font-size: 20px;
        font-weight: bold;
        color: white;
        z-index: 2;
        text-shadow: 2px 2px 6px rgba(0,0,0,0.7);
        font-family: 'Noto Sans KR', sans-serif;
    }

    .image-credit {
        position: absolute;
        bottom: 8px;
        left: 15px;
        font-size: 11px;
        font-family: 'Noto Sans KR', sans-serif;
        color: white;
        background-color: rgba(0, 0, 0, 0.5);
        padding: 3px 6px;
        border-radius: 5px;
        opacity: 0;
        transition: opacity 0.3s ease;
        z-index: 2;
    }

    &:hover .image-credit {
        opacity: 1;
    }
`;

export const StyledImage = styled.img`
    width: 100%;
    height: 500px;
    object-fit: cover;
    border-radius: 5px;
`;

export const ContentsTitle = styled.div`
    padding: 40px 40px;
    text-align: center;

    > h1 {
        padding: 40px 80px;
        font-family:'Noto Sans KR', sans-serif;
    }
`;

export const ImageTextWrapper = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 40px 0;

    background: linear-gradient(135deg, #fdfbfb 50%, #ebedee 100%);
    border: 2px solid #f2f2f2;
    border-radius: 20px;
    padding: 40px 30px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease;

    @media (max-width: 768px) {
        flex-direction: column;
        padding: 30px 20px;
    }
`;

export const LeftImage = styled.div`
    flex: 1;
    padding-right: 30px;

    img {
        width: 65%;
        height: auto;
        border-radius: 120px;
        border: 8px solid ${({ borderColor }) => borderColor};
        box-shadow: 0 3px 25px rgba(0, 0, 0, 0.3);
        transition: transform 0.4s ease-in-out;

        &:hover {
            transform: rotate(0.4deg) scale(1.03);
        }
    }
`;

export const RightText = styled.div`
    flex: 0.9;
    text-align: left;
    font-size: 1.4rem;
    line-height: 1.5;
    font-family: 'Noto Sans KR', sans-serif;
`;

export const ServiceGrid = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    gap: 20px;
    margin-top: 50px;
    padding: 20px;
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.15);
`;

export const ServiceCard = styled(Link)`
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    background-color: transparent;
    text-decoration: none;
    color: inherit;
    overflow: hidden;

    img {
        width: 182px;
        height: 240px;
        object-fit: cover;
        margin-bottom: 6px;
        transition: transform 0.3s ease, box-shadow 0.3s ease, filter 0.3s ease;

        &:hover {
            transform: scale(0.95);
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.4);
            filter: brightness(50%);
        }
    }

    h2 {
        position: absolute;
        top: 45%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-size: 17px;
        font-family: 'Noto Sans KR', sans-serif;
        color: white;
        font-weight: bold;
        pointer-events: none;
        z-index: 1;
        text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.9);
    }
`;

export const HeaderSpacer = styled.div`
    height: 180px;
`;