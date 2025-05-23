// WeatherSection.style.js
import styled from 'styled-components';
import { getSeason, theme } from "../components/theme.js";

const season = getSeason();
const currentTheme = theme[season];

export const WeatherSectionWrap = styled.div`
    position: fixed;
    top: 180px;
    right: 10px;
    width: ${(props) => (props.width ? `${props.width}px` : '150px')};
    padding: 20px;
    border: 1.5px solid ${currentTheme.borderColor};
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    border-radius: 12px;
    text-align: center;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #333;
    user-select: none;
    z-index: 1000;

    p {
        margin: 10px 0;
        font-size: 1rem;
        line-height: 1.3;
    }
`;
