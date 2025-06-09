import styled from 'styled-components';

export const ContentsWrapper = styled.div`
    padding-top: 185px; /* 메뉴에 가려지지 않도록 */
`;

export const CategoryMenu = styled.div`
    display: flex;
    justify-content: center;
    gap: 5px;
    margin-bottom: 30px;
`;

export const CategoryButton = styled.button`
    padding: 3px 22px;
    font-size: 13px;
    background-color: ${props => (props.active ? '#ddd' : '#f5f5f5')};
    color: ${props => (props.active ? '#000' : '#333')};
    border: 1px solid #ccc;
    border-radius: 10px;
    cursor: pointer;
    &:hover {
        background-color: #ddd;
    }
`;

export const MainContents = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    max-width: 1400px;
    margin: 0 auto;
    padding: 20px;
`;

export const Card = styled.div`
    box-sizing: border-box;
    transition: transform .1s, background-color .6s;
    margin: 50px;
    height: auto;
    text-align: center;
    width: 300px;
    border: 1px solid #e0e0e0;
    border-radius: 10px;
    overflow: hidden;
    font-family: Arial, sans-serif;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

    &:hover {
        transform: scale(1.1);
    }
`;

export const Content = styled.div`
    padding: 16px;
`;

export const Like = styled.div`
    color: #ff0000;
    font-size: 20px;
    cursor: pointer;
`;

export const ClothesText = styled.p`
    font-size: 14px;
    color: #333;
    margin-top: 8px;
    line-height: 1.4;
`;
