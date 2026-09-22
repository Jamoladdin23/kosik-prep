import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    // Any request to /api/* from the dev server (http://localhost:5173) is forwarded to the
    // Spring Boot backend on :8080. The browser only ever talks to :5173, so there is no CORS
    // issue in development — the same trick Django/Vue setups do with a reverse proxy.
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
})
