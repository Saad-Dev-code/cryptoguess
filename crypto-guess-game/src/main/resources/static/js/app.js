// /static/js/app.js

// 1) Ripple for buttons
document.addEventListener('click', (e) => {
  const t = e.target.closest('.ripple');
  if (!t) return;
  // trigger CSS :active + after; nothing else needed
});

// 2) Tilt effect (tiny)
function initTilt() {
  const cards = document.querySelectorAll('.tilt');
  cards.forEach(card => {
    let rect;
    card.addEventListener('mouseenter', () => rect = card.getBoundingClientRect());
    card.addEventListener('mousemove', (e) => {
      if (!rect) rect = card.getBoundingClientRect();
      const x = e.clientX - rect.left, y = e.clientY - rect.top;
      const rx = ((y / rect.height) - .5) * -10;
      const ry = ((x / rect.width)  - .5) *  10;
      card.style.transform = `perspective(700px) rotateX(${rx}deg) rotateY(${ry}deg)`;
    });
    card.addEventListener('mouseleave', () => {
      card.style.transform = '';
    });
  });
}

// 3) Clickable answer cards -> submit hidden form
function initClickableCards() {
  const form = document.getElementById('answerForm');
  const input = document.getElementById('choiceInput');
  if (!form || !input) return;
  document.querySelectorAll('.clickable[data-choice]').forEach(card => {
    card.addEventListener('click', () => {
      // tiny tap feedback
      card.animate([{transform:'scale(1)'},{transform:'scale(0.98)'},{transform:'scale(1)'}],
                   {duration:160, easing:'ease-out'});
      input.value = card.getAttribute('data-choice');
      form.submit();
    });
  });
}

// 4) Confetti on correct results
function initConfettiOnResult() {
  const isCorrect = document.body.getAttribute('data-correct');
  if (isCorrect !== 'true') return;

  // minimal confetti (no dependencies)
  const canvas = document.createElement('canvas');
  canvas.style.position = 'fixed';
  canvas.style.inset = '0';
  canvas.style.pointerEvents = 'none';
  canvas.width = window.innerWidth; canvas.height = window.innerHeight;
  document.body.appendChild(canvas);
  const ctx = canvas.getContext('2d');

  const N = 120;
  const parts = Array.from({length:N}, ()=>({
    x: Math.random()*canvas.width,
    y: -20 - Math.random()*80,
    r: 6+Math.random()*6,
    c: `hsl(${Math.random()*360},80%,60%)`,
    vx: -2+Math.random()*4,
    vy: 2+Math.random()*3,
    spin: Math.random()*Math.PI
  }));

  let t = 0, maxT = 120; // ~2s @60fps
  function tick(){
    ctx.clearRect(0,0,canvas.width,canvas.height);
    parts.forEach(p=>{
      p.x += p.vx; p.y += p.vy; p.vy += 0.05; p.spin += 0.2;
      ctx.save();
      ctx.translate(p.x,p.y);
      ctx.rotate(p.spin);
      ctx.fillStyle = p.c;
      ctx.fillRect(-p.r/2,-p.r/2,p.r,p.r);
      ctx.restore();
    });
    t++;
    if (t < maxT) requestAnimationFrame(tick);
    else canvas.remove();
  }
  tick();
}

document.addEventListener('DOMContentLoaded', () => {
  initTilt();
  initClickableCards();
  initConfettiOnResult();
});

// Pop the score value if the last guess was correct
function popScoreIfCorrect() {
  const correct = document.body.getAttribute('data-correct');
  if (correct === 'true') {
    const v = document.querySelector('.hud-value');
    if (v){ v.classList.add('bounce'); setTimeout(()=>v.classList.remove('bounce'), 600); }
  }
}

document.addEventListener('DOMContentLoaded', () => {
  // existing init...
  popScoreIfCorrect();
});
