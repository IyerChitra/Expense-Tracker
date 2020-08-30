import React from 'react';
import { ListItem } from 'react-native-elements';
import { ScrollView } from 'react-native';
import LinearGradient from 'react-native-linear-gradient';
import FA5Icon from 'react-native-vector-icons/FontAwesome5';
import IonIcons from 'react-native-vector-icons/Ionicons';
import EntypoIcon from 'react-native-vector-icons/Entypo';
import PropTypes from 'prop-types';

const list = [
  {
    name: 'Wallet 1',
    status: 'active',
    balance: 1000
  },
  {
    name: 'Wallet 2',
    status: 'active',
    balance: 100
  },
  {
    name: 'Wallet 3',
    status: 'inactive',
    balance: 1000
  },
  {
    name: 'Wallet 4',
    status: 'active',
    balance: 1000
  },
  {
    name: 'Wallet 5',
    status: 'active',
    balance: 1000
  },
  {
    name: 'Wallet 6',
    status: 'inactive',
    balance: 0
  },
  {
    name: 'Wallet 7',
    status: 'active',
    balance: 1000
  },
  {
    name: 'Wallet 8',
    status: 'inactive',
    balance: 100
  },
  {
    name: 'Wallet 9',
    status: 'inactive',
    balance: 1000
  }
];

const WalletsComponent = ({ navigation }) => (
  <ScrollView showsVerticalScrollIndicator={false}>
    {list.map((item, i) => (
      <ListItem
        onPress={() => navigation.navigate('My Wallet')}
        key={i}
        linearGradientProps={{
          colors: ['#ffffff', '#ffffff'],
          start: { x: 0.8, y: 0 },
          end: { x: 0.1, y: 0 }
        }}
        ViewComponent={LinearGradient}
        bottomDivider
        containerStyle={{
          backgroundColor: 'grey'
        }}
      >
        <FA5Icon name="money-check" color="black" size={20} />
        <ListItem.Content>
          <ListItem.Title style={{ color: 'grey', fontWeight: 'bold' }}>
            {item.name}
          </ListItem.Title>
          <ListItem.Subtitle style={{ color: 'grey', paddingTop: 10 }}>
            status:{' '}
            {item.status === 'inactive' ? (
              <EntypoIcon name="circle-with-cross" color="#db5537" size={16} />
            ) : (
              <IonIcons name="checkmark-circle" color="green" size={16} />
            )}
          </ListItem.Subtitle>
        </ListItem.Content>
        <ListItem.Content style={{ flex: 1, left: 30, flexDirection: 'row' }}>
          <FA5Icon name="rupee-sign" size={15} />
          {item.balance && item.balance > 100 ? (
            <ListItem.Title
              style={{
                color: 'green',
                fontWeight: 'bold',
                top: -3,
                paddingLeft: 10
              }}
            >
              +{item.balance}
            </ListItem.Title>
          ) : (
            <ListItem.Title
              style={{
                color: 'red',
                fontWeight: 'bold',
                top: -3,
                paddingLeft: 10
              }}
            >
              -{item.balance || `0.00`}
            </ListItem.Title>
          )}
        </ListItem.Content>
        <ListItem.Chevron color="black" />
      </ListItem>
    ))}
  </ScrollView>
);

WalletsComponent.propTypes = {
  navigation: PropTypes.object
};

export default WalletsComponent;
