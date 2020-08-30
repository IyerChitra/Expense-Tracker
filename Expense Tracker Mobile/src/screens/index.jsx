/* eslint-disable react/no-unused-prop-types */
/* eslint-disable react/require-default-props */
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import React from 'react';
import PropTypes from 'prop-types';
import { getFocusedRouteNameFromRoute } from '@react-navigation/native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import ProfileScreen from './ProfileScreen';
import WalletsScreen from './WalletsScreen';
import WalletDetailScreen from './WalletDetailScreen';

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();

const StackNavigator = () => (
  <Stack.Navigator initialRouteName="Home" headerMode="none">
    <Stack.Screen name="Home" component={TabNavigator} />
    <Stack.Screen name="Wallets" component={WalletsScreen} />
    <Stack.Screen name="My Wallet" component={WalletDetailScreen} />
  </Stack.Navigator>
);

const TabNavigator = ({ navigation, route }) => {
  return (
    <Tab.Navigator
      initialRouteName="Home"
      screenOptions={({ route }) => ({
        tabBarIcon: ({ focused, color, size }) => {
          let iconName;
          if (route.name === 'Wallets') {
            iconName = focused ? 'ios-wallet' : 'ios-wallet-outline';
          } else if (route.name === 'Profile') {
            iconName = focused ? 'person-circle' : 'person-circle-outline';
          }
          return <Ionicons name={iconName} size={size} color={color} />;
        }
      })}
      tabBarOptions={{
        activeTintColor: 'tomato',
        inactiveTintColor: 'gray',
        style: {
          height: 60,
          paddingBottom: 10,
          maxHeight: 60
        }
      }}
    >
      <Tab.Screen name="Wallets" component={WalletsScreen} />
      <Tab.Screen name="Profile" component={ProfileScreen} />
    </Tab.Navigator>
  );
};

TabNavigator.propTypes = {
  navigation: PropTypes.object || PropTypes.func,
  route: PropTypes.object,
  focused: PropTypes.bool,
  color: PropTypes.string,
  size: PropTypes.number
};

export {
  StackNavigator as AppStackNavigator,
  TabNavigator as AppBottomTabNavigator
};
