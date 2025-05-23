import React from 'react';
import {WeatherSectionWrap} from "../layout/WeatherSection.style.js";

const WeatherSection = ({ weatherData, address }) => {
    const now = new Date();
    const hour = now.getHours();
    const fcstTime = String(hour).padStart(2, '0') + '00';

    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');

    const currentWeather = weatherData?.hourlyTemperatures?.find(w =>
        w.month === month && w.day === day && w.time === fcstTime
    );

    if (!currentWeather) {
        return <WeatherSectionWrap>지역을 검색해주세요</WeatherSectionWrap>;
    }

    return (
        <WeatherSectionWrap>
            <p>{address}</p>
            <p>날씨: {currentWeather.weatherType}</p>
            <p>기온: {currentWeather.temperature}°C</p>
        </WeatherSectionWrap>
    );
};

export default WeatherSection;