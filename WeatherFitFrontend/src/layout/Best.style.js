import styled from 'styled-components';

// 전체 콘텐츠 감싸는 영역 (푸터가 위로 올라오는 것 방지)
export const ContentsWrapper = styled.div`
    min-height: calc(100vh - 300px); // 헤더+푸터 제외한 최소 높이 확보
    display: flex;
    justify-content: center;
    align-items: flex-start;
`;

// 옷 카드 리스트 정렬
export const MainContents = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    max-width: 1400px;
    margin: 0 auto;
    padding: 40px 20px;
`;

// 카드 하나
export const Card = styled.div`
    box-sizing: border-box;
    transition: transform 0.1s, background-color 0.6s;
    margin: 50px;
    text-align: center;
    width: 350px;
    height: 500px;
    border: 1px solid #e0e0e0;
    border-radius: 10px;
    overflow: hidden;
    font-family: Arial, sans-serif;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);

    &:hover {
        transform: scale(1.1);
    }
`;

// 이미지 영역
export const Wimg = styled.div`
    width: 100%;
    height: 420px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;

    img {
        max-height: 100%;
        max-width: 100%;
        object-fit: contain;
        display: block;
    }
`;

// 카드 내부 텍스트 영역
export const Content = styled.div`
    padding: 16px;
`;

// 좋아요 텍스트
export const Like = styled.div`
    color: #ff0000;
    font-size: 20px;
    cursor: pointer;
`;

// 옷 이름 텍스트
export const ClothesText = styled.p`
    font-size: 14px;
    color: #333;
    line-height: 1.4;
`;

// 추천된 옷이 없을 때 메시지 표시용
export const MessageWrapper = styled.div`
  width: 100%;
  text-align: center;
  padding: 50px 0;
  font-size: 18px;
  color: #666;
`;
