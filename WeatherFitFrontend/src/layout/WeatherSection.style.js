import styled from 'styled-components';

export const WeatherCardWrap = styled.div`
    position: fixed;
    top: 270px;
    right: 20px;
    width: 160px;
    height: 167px;
    background: linear-gradient(to bottom, #dfefff, #ffffff);
    border-radius: 20px;
    padding: 20px 10px;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
    text-align: center;
    display: flex;
    flex-direction: column;
    font-family: 'Segoe UI', sans-serif;
    z-index: 999;
`;

export const RegionText = styled.div`
    font-size: 13px;
    color: #666;
    font-weight: 500;
`;

export const WeatherTextMain = styled.div`
    font-size: 16px;
    font-weight: 600;
    margin-top: 5px;
`;

export const IconAndTempRow = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 6px;
`;

export const IconBox = styled.div`
    svg {
        width: 42px;
        height: 42px;
    }
`;

export const TemperatureBig = styled.div`
    font-size: 36px;
    font-weight: bold;
    color: #111;
`;

export const TimeLabel = styled.div`
    font-size: 13px;
    margin-top: 20px;
    color: #888;
`;
export const ModalContainer = styled.div`
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 30px;
    border: 1px solid #ccc;
    z-index: 1000;
    box-shadow: 0 4px 15px rgba(0,0,0,0.3);
    border-radius: 30px;
    width: 70%;
    height: 60%;
`;
export const ModalOverlay = styled.div`
    position: fixed;
    top: 0; left: 0; right: 0; bottom: 0;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 999;
`;
export const ModalCloseBtn = styled.div`
    position: absolute;
    top: -10px;
    right: -10px;
`;
export const WeatherCardRow = styled.div`
  display: flex;
  gap: 20px; /* 카드 사이 간격 */
  justify-content: center;
  flex-wrap: wrap; /* 화면 작아지면 줄바꿈 */
`;

export const HorizontalScrollContainer = styled.div`
  display: flex;
  overflow-x: auto;
  padding: 1rem;
  gap: 1rem;
`;

export const HourCard = styled.div`
  min-width: 80px;
  padding: 10px;
  background-color: white;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
`;

export const TimeText = styled.div`
  font-size: 14px;
  color: #555;
`;

export const TempText = styled.div`
  font-size: 18px;
  font-weight: bold;
  margin-top: 4px;
`;

export const WeatherTextHour = styled.div`
    font-size: 13px;
    color: #666;
    margin-top: 4px;
`;