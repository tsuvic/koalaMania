// 表示要素 DOM操作用定数
const contents = document.querySelector('.koalaList');
const redraw_elements = document.querySelectorAll('div.koalaList > div');

// ページネーション DOM操作用の定数
const total_el = document.querySelector('.total_counter');
const page_counter = document.querySelector('.page_counter');
const prev_btn = document.querySelector('.prev');
const next_btn = document.querySelector('.next');
const count = 30;

//グローバル変数
let current_step;
let index_start;
let index_end;

// ページ数を算出する
// total_stepという変数に合計のページ数を割り当て
// current_stepで現在のページ番号を定義
// current_step_updateはページネーションの次へ前へのボタンがクリックされた際に渡される値
function split_page(current_step_update){
  total_step = Math.ceil(redraw_elements.length / count);
  if( current_step_update === undefined || current_step === 1){
    current_step = 1;
    next_btn_disable(); prev_btn_active();
  } else if( current_step_update === total_step ) {
    next_btn_active(); prev_btn_disable();
  } else {
    current_step = current_step_update;
    next_btn_disable(); prev_btn_disable();
    console.log(current_step);
  }

  total_el.textContent = current_step + '/' + total_step;
  redraw(redraw_elements.length, total_step, current_step, count);
}

// DOMの描画
function redraw(total, total_step, current_step, count)
{
  //現在の表示indexを割り出す
  index_start = current_step * count - count;
  index_end = current_step * count - 1;
  let index_array = [];
  for (let i = index_start; i < index_end + 1; i++) {
      index_array.push(i);
  }

  //一時削除
  while( contents.lastChild ) {
    contents.lastChild.remove();
  }

  //再描画
  redraw_elements.forEach((element, index) => {
    if(index_array.indexOf(index) != -1){
      contents.appendChild(element);
    }
  });
}

// ページカウンターの作成
function create_page_counter()
{
  for (let i = 1; i < Math.ceil(redraw_elements.length / count) + 1; i++){
    let count_list = document.createElement('li');
    count_list.setAttribute('data-counter-id', i);
    count_list.classList.add('page_number');
    count_list.textContent = i;
    page_counter.appendChild(count_list);
  }
}

//次へや前へが押された時のイベント処理
next_btn.addEventListener('click', ()=> {
  split_page(current_step += 1);
});

prev_btn.addEventListener('click', ()=> {
  split_page(current_step -= 1);
});

//class付与・削除関数
prev_btn_active = () => {
  prev_btn.classList.add('disable');
}
prev_btn_disable = () => {
  prev_btn.classList.remove('disable');
}
next_btn_disable = () => {
  next_btn.classList.remove('disable');
}
next_btn_active = () => {
  next_btn.classList.add('disable');
}

//DOMの構築が完了したタイミングでページネーション実行
window.addEventListener('DOMContentLoaded', () => {
  split_page();
  create_page_counter();
  document.querySelectorAll('.page_number').forEach((element, index) => {
    element.addEventListener('click', function(e) {
      current_step = Number(element.getAttribute('data-counter-id'));
      split_page(current_step);
    })
  })
})
