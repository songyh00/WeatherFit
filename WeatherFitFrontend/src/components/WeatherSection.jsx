import React from 'react';
import { WeatherSectionWrap } from "../layout/WeatherSection.style.js";
import WeatherDisplay from "./WeatherType.jsx";

const WeatherSection = ({ weatherData, address }) => {
    const now = new Date();
    const hour = now.getHours();
    const fcstTime = String(hour).padStart(2, '0') + '00';

    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');

    const currentWeather = weatherData?.hourlyTemperatures?.find(w =>
        w.month === month && w.day === day && w.time === fcstTime
    );

    const baseWidth = 150;
    const charWidth = 17;
    const maxWidth = 300;
    const calculatedWidth = Math.min(maxWidth, Math.max(baseWidth, address.length * charWidth));

    console.log("calculatedWidth", calculatedWidth); // 값 확인

    if (!currentWeather) {
        return <WeatherSectionWrap width={calculatedWidth}>지역을 검색해주세요</WeatherSectionWrap>;
    }

    return (
        <WeatherSectionWrap width={calculatedWidth}>
            <p>{address}</p>
            <p>
                날씨:&nbsp;
                <WeatherDisplay weatherType={currentWeather.weatherType} />
                {currentWeather.weatherType}
            </p>
            <p>기온: {currentWeather.temperature}°C</p>
        </WeatherSectionWrap>
    );
};

export default WeatherSection;
