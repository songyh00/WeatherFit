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

export const WeatherText = styled.div`
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
