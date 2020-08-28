module.exports = {
  parser: 'babel-eslint',
  env: {
    browser: true,
    es2020: true
  },
  extends: ['plugin:react/recommended', 'airbnb'],
  parserOptions: {
    ecmaFeatures: {
      jsx: true
    },
    ecmaVersion: 12,
    sourceType: 'module'
  },
  plugins: ['react'],
  rules: {
    quotes: ['error', 'single', { avoidEscape: true }],
    'comma-dangle': ['error', 'never'],
    'no-use-before-define': ['error', { variables: false }],
    'react/jsx-filename-extension': [1, { extensions: ['.js', '.jsx'] }]
  }
};
