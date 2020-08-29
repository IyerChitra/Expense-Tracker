/* eslint-disable no-undef */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import { View } from 'react-native';

import { NavigationContainer } from '@react-navigation/native';
import { AppStackNavigator, AppBottomTabNavigator } from './screens';

const AppNavigator = () => (
  <View style={{ flex: 1 }}>
    <NavigationContainer>
      <AppStackNavigator />
      {/* <AppBottomTabNavigator /> */}
    </NavigationContainer>
  </View>
);

export default AppNavigator;
