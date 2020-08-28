import { createStackNavigator } from '@react-navigation/stack';
import React from 'react';
import WelcomeScreen from './WelcomeScreen';

const Stack = createStackNavigator();

const StackNavigator = () => (
  <Stack.Navigator>
    <Stack.Screen name="Home" component={WelcomeScreen} />
  </Stack.Navigator>
);
export default StackNavigator;
